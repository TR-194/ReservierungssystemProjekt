import { Injectable } from '@angular/core';
import { ApiService } from '../services/api.service';
import { Observable } from 'rxjs';
import { Buchung } from '../models/buchung.model';

@Injectable({
  providedIn: 'root'
})
export class BuchungService {
  private endpoint = 'buchungen';

  constructor(private apiService: ApiService) {}

  getBuchungen(): Observable<Buchung[]> {
    return this.apiService.get<Buchung[]>(this.endpoint);
  }

  getBuchungById(id: number): Observable<Buchung> {
    return this.apiService.get<Buchung>(`${this.endpoint}/${id}`);
  }

  createBuchung(buchung: Buchung): Observable<Buchung> {
    return this.apiService.post<Buchung>(this.endpoint, buchung);
  }

  updateBuchung(id: number, buchung: Buchung): Observable<Buchung> {
    return this.apiService.put<Buchung>(`${this.endpoint}/${id}`, buchung);
  }

  deleteBuchung(id: number): Observable<void> {
    return this.apiService.delete(`${this.endpoint}/${id}`);
  }
}
