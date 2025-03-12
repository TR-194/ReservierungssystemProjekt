import { Injectable } from '@angular/core';
import { Client, Message, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Subject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private stompClient: Client | undefined;
  private kafkaResponseSubject = new Subject<any>(); // Subject für Kafka-Antworten

  constructor() {
    this.connect();
  }

  private connect() {
    const socket = new SockJS('http://localhost:8080/ws/kafka'); // WebSocket-Endpoint
    this.stompClient = Stomp.over(socket);

    this.stompClient.onConnect = () => {
      console.log('WebSocket mit Kafka verbunden!');
      this.subscribeToKafkaResponses();
    };

    this.stompClient.onWebSocketError = (error) => {
      console.error('WebSocket-Fehler:', error);
    };

    this.stompClient.activate();
  }

  private subscribeToKafkaResponses() {
    if (!this.stompClient) return;

    this.stompClient.subscribe('/topic/kafkaResponse', (message: Message) => {
      console.log('Kafka Nachricht empfangen:', message.body);
      const response = JSON.parse(message.body);
      this.kafkaResponseSubject.next(response); // Kafka-Antworten an KafkaService weiterleiten
    });
  }

  sendKafkaRequest(eventType: string, payload?: any) {
    if (this.stompClient?.connected) {
      const request = {
        eventType,
        payload
      };
      this.stompClient.publish({ destination: '/app/kafkaRequest', body: JSON.stringify(request) });
    } else {
      console.error('WebSocket ist nicht verbunden.');
    }
  }

  getKafkaResponses(): Observable<any> {
    return this.kafkaResponseSubject.asObservable();
  }
}
