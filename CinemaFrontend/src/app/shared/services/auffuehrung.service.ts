import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Auffuehrung } from '../models/auffuehrung.model';

@Injectable({
  providedIn: 'root'
})
export class AuffuehrungService {
  private apiUrl = 'http://localhost:8080/api/auffuehrungen'; // Backend-Endpunkt

  constructor(private http: HttpClient) {}

  getAuffuehrungen(): Observable<Auffuehrung[]> {
    return this.http.get<Auffuehrung[]>(this.apiUrl);
  }

  getAuffuehrungById(id: number): Observable<Auffuehrung> {
    return this.http.get<Auffuehrung>(`${this.apiUrl}/${id}`);
  }
}
