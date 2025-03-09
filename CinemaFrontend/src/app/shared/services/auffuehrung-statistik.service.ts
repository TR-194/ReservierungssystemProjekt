import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';

interface AuffuehrungStatistik {
  id: number;
  name: string;
  einnahmen: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuffuehrungStatistikService {
  private endpoint = 'auffuehrung-statistik';

  constructor(private apiService: ApiService) {}

  getAuffuehrungStatistik(): Observable<AuffuehrungStatistik[]> {
    return this.apiService.get<AuffuehrungStatistik[]>(this.endpoint);
  }
}
