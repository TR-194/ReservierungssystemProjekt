import { Injectable } from '@angular/core';
import { Client, Message, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Subject, Observable, retry } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Client | undefined;
  private kafkaResponseSubject = new Subject<any>();
  private RECONNECT_INTERVAL = 5000; // 5 Sekunden

  constructor() {
    this.connect();
  }

  private connect() {
    const socket = new SockJS('http://localhost:8080/ws/kafka');
    this.stompClient = Stomp.over(socket);
    this.stompClient.reconnectDelay = this.RECONNECT_INTERVAL;

    this.stompClient.onConnect = () => {
      console.log('WebSocket mit Kafka verbunden!');
      this.subscribeToKafkaResponses();
    };

    this.stompClient.onWebSocketError = (error) => {
      console.error('WebSocket-Fehler:', error);
      setTimeout(() => this.connect(), this.RECONNECT_INTERVAL); // Automatischer Reconnect
    };

    this.stompClient.activate();
  }

  private subscribeToKafkaResponses() {
    if (!this.stompClient) return;
    this.stompClient.subscribe('/topic/kafkaResponse', (message: Message) => {
      console.log('Kafka Nachricht empfangen:', message.body);
      this.kafkaResponseSubject.next(JSON.parse(message.body));
    });
  }

  sendKafkaRequest(eventType: string, payload?: any) {
    if (this.stompClient?.connected) {
      this.stompClient.publish({ destination: '/app/kafkaRequest', body: JSON.stringify({ eventType, payload }) });
    } else {
      console.error('WebSocket ist nicht verbunden.');
    }
  }

  getKafkaResponses(): Observable<any> {
    return this.kafkaResponseSubject.asObservable().pipe(retry({ count: 3, delay: this.RECONNECT_INTERVAL }));
  }
}
