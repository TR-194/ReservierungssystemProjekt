import { Injectable } from '@angular/core';
import { WebSocketService } from './websocket.service';
import { Observable, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class KafkaService {
  private responseMap = new Map<string, Subject<any>>();

  constructor(private webSocketService: WebSocketService) {
    this.webSocketService.getKafkaResponses().subscribe(response => {
      const { requestId, data } = response;
      if (this.responseMap.has(requestId)) {
        this.responseMap.get(requestId)?.next(data);
        this.responseMap.delete(requestId);
      }
    });
  }

  sendRequest<T>(eventType: string, payload?: any): Observable<T> {
    const requestId = this.generateRequestId();
    const responseSubject = new Subject<T>();
    this.responseMap.set(requestId, responseSubject);

    this.webSocketService.sendKafkaRequest(eventType, payload);

    return responseSubject.asObservable();
  }

  private generateRequestId(): string {
    return Math.random().toString(36).substring(7);
  }
}
