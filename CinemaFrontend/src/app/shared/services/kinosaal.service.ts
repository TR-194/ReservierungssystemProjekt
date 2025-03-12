import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Kinosaal } from '../models/kinosaal.model';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class KinosaalService {
  private endpoint = 'kinosaele';

  constructor(private apiService: ApiService) {}

  // Alle Kinosäle abrufen
  getKinosaele(): Observable<Kinosaal[]> {
    return this.apiService.get<Kinosaal[]>(this.endpoint);
  }

  // Einzelnen Kinosaal abrufen
  getKinosaalById(id: number): Observable<Kinosaal> {
    return this.apiService.get<Kinosaal>(`${this.endpoint}/${id}`);
  }

  // Neuen Kinosaal erstellen
  erstelleKinosaal(kinosaal: Kinosaal): Observable<Kinosaal> {
    return this.apiService.post<Kinosaal, Kinosaal>(this.endpoint, kinosaal);
  }

  // Kinosaal aktualisieren
  updateKinosaal(id: number, kinosaal: Kinosaal): Observable<Kinosaal> {
    return this.apiService.put<Kinosaal, Kinosaal>(`${this.endpoint}/${id}`, kinosaal);
  }

  // Kinosaal löschen
  deleteKinosaal(id: number): Observable<void> {
    return this.apiService.delete(`${this.endpoint}/${id}`);
  }

  // Kinosaal freigeben
  freigeben(id: number): Observable<void> {
    return this.apiService.put<void, void>(`${this.endpoint}/${id}/freigeben`, {});
  }
}