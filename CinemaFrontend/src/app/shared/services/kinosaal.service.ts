import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Kinosaal } from '../models/kinosaal.model';

@Injectable({
  providedIn: 'root'
})
export class KinosaalService {
  private apiUrl = 'http://localhost:3000/kinosaele';

  constructor(private http: HttpClient) {}

  getKinosäle(): Observable<Kinosaal[]> {
    return this.http.get<Kinosaal[]>(this.apiUrl);
  }

  erstelleKinosaal(kinosaal: Kinosaal): Observable<Kinosaal> {
    return this.http.post<Kinosaal>(this.apiUrl, kinosaal);
  }
}