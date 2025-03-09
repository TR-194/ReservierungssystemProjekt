import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Film } from '../models/film.model';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root'
})
export class FilmService {
  private endpoint = 'filme';

  constructor(private apiService: ApiService) {}

  getFilme(): Observable<Film[]> {
    return this.apiService.get<Film[]>(this.endpoint);
  }

  getFilmById(id: number): Observable<Film> {
    return this.apiService.get<Film>(`${this.endpoint}/${id}`);
  }

  erstelleFilm(film: Film): Observable<Film> {
    return this.apiService.post<Film, Film>(this.endpoint, film);
  }

  bearbeiteFilm(id: number, film: Film): Observable<Film> {
    return this.apiService.put<Film, Film>(`${this.endpoint}/${id}`, film);
  }

  loescheFilm(id: number): Observable<void> {
    return this.apiService.delete(`${this.endpoint}/${id}`);
  }
}