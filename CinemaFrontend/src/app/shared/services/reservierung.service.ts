import { Injectable } from '@angular/core';
import { ApiService } from '../services/api.service';
import { Observable } from 'rxjs';
import { Reservierung } from '../models/reservierung.model';
import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ReservierungService {
  private endpoint = 'reservierungen';

  constructor(private apiService: ApiService) {}

  getReservierungen(): Observable<Reservierung[]> {
    return this.apiService.get<Reservierung[]>(this.endpoint);
  }

  getReservierungById(id: number): Observable<Reservierung> {
    return this.apiService.get<Reservierung>(`${this.endpoint}/${id}`);
  }

  createReservierung(reservierung: Reservierung): Observable<Reservierung> {
    return this.apiService.post<Reservierung, Reservierung>(this.endpoint, reservierung);
  }

  updateReservierung(id: number, reservierung: Reservierung): Observable<Reservierung> {
    return this.apiService.put<Reservierung, Reservierung>(`${this.endpoint}/${id}`, reservierung);
  }

  deleteReservierung(id: number): Observable<void> {
    return this.apiService.delete(`${this.endpoint}/${id}`);
  }

  getReservierungByEmail(email: string): Observable<Reservierung> {
    const params = new HttpParams().set('email', email);
    return this.apiService.get<Reservierung>(`${this.endpoint}/by-email`, { params });
  }
}
