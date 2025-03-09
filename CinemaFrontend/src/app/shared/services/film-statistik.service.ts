import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

interface FilmStatistik {
  id: number;
  titel: string;
  einnahmen: number;
}

@Injectable({
  providedIn: 'root'
})
export class FilmStatistikService {
  private endpoint = 'film-statistik';

  constructor(private apiService: ApiService) {}

  getFilmStatistik(): Observable<FilmStatistik[]> {
    return this.apiService.get<FilmStatistik[]>(this.endpoint);
  }
}
