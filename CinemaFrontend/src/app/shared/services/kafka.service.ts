import { Injectable } from '@angular/core';
import { WebSocketService } from './websocket.service';
import { Observable, Subject, throwError, timeout, catchError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KafkaService {
  private responseMap = new Map<string, Subject<any>>();
  private REQUEST_TIMEOUT = 5000; // 5 Sekunden Timeout

  constructor(private webSocketService: WebSocketService) {
    this.webSocketService.getKafkaResponses().subscribe(response => {
      const { requestId, data, error } = response;
      if (this.responseMap.has(requestId)) {
        if (error) {
          this.responseMap.get(requestId)?.error(new Error(error));
        } else {
          this.responseMap.get(requestId)?.next(data);
        }
        this.responseMap.get(requestId)?.complete();
        this.responseMap.delete(requestId);
      }
    });
  }

  sendRequest<T>(eventType: string, payload?: any): Observable<T> {
    const requestId = this.generateRequestId();
    const responseSubject = new Subject<T>();
    this.responseMap.set(requestId, responseSubject);

    this.webSocketService.sendKafkaRequest(eventType, { requestId, ...payload });

    return responseSubject.asObservable().pipe(
      timeout(this.REQUEST_TIMEOUT),
      catchError(err => {
        this.responseMap.delete(requestId);
        return throwError(() => new Error(`Kafka Request Timeout oder Fehler: ${err.message}`));
      })
    );
  }

  private generateRequestId(): string {
    return Math.random().toString(36).substring(2, 15);
  }
}
