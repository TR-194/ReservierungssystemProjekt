import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Auffuehrung } from '../models/auffuehrung.model';

@Injectable({
  providedIn: 'root'
})
export class AuffuehrungService {
  private endpoint = 'auffuehrungen';

  constructor(private apiService: ApiService) {}

  getAuffuehrungen(): Observable<Auffuehrung[]> {
    return this.apiService.get<Auffuehrung[]>(this.endpoint);
  }

  getAuffuehrungById(id: number): Observable<Auffuehrung> {
    return this.apiService.get<Auffuehrung>(`${this.endpoint}/${id}`);
  }

  addAuffuehrung(auffuehrung: Auffuehrung): Observable<Auffuehrung> {
    return this.apiService.post<Auffuehrung, Auffuehrung>(this.endpoint, auffuehrung);
  }

  deleteAuffuehrung(id: number): Observable<void> {
    return this.apiService.delete(`${this.endpoint}/${id}`);
  }
}
