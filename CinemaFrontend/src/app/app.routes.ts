import { Route } from '@angular/router';
import { AuffuehrungListComponent } from './kunde/auffuehrung/auffuehrung-list/auffuehrung-list.component';
import { AuffuehrungDetailComponent } from './kunde/auffuehrung/auffuehrung-detail/auffuehrung-detail.component';
import { ReservierungFormComponent } from './kunde/reservierung/reservierung-form/reservierung-form.component';
import { ReservierungSucheComponent } from './kunde/reservierung/reservierung-suche/reservierung-suche.component';
import { AdminDashboardComponent } from './admin/dashboard/admin-dashboard/admin-dashboard.component';
import { AdminKinosaalFormComponent } from './admin/kinosaal/admin-kinosaal-form/admin-kinosaal-form.component';
import { AdminStatistikAuffuehrungComponent } from './admin/statistik/admin-stastistik-auffuehrung/admin-stastistik-auffuehrung.component';
import { BuchungFormComponent } from './kunde/buchung/buchung-form/buchung-form.component';
import { AdminKinosaalListComponent } from './admin/kinosaal/admin-kinosaal-list/admin-kinosaal-list.component';
import { AdminStastistikComponent } from './admin/statistik/admin-stastistik/admin-stastistik.component';
import { AdminStatistikFilmComponent } from './admin/statistik/admin-stastistik-film/admin-stastistik-film.component';
import { AdminAuffuehrungListComponent } from './admin/auffuehrungen/admin-auffuehrung-list/admin-auffuehrung-list.component';
import { AdminAuffuehrungFormComponent } from './admin/auffuehrungen/admin-auffuehrung-form/admin-auffuehrung-form.component';
import { AdminFilmListComponent } from './admin/filme/admin-film-list/admin-film-list.component';
import { AdminFilmFormComponent } from './admin/filme/admin-film-form/admin-film-form.component';

export const appRoutes: Route[] = [
    { path: '', component: AuffuehrungListComponent},
    { path: 'auffuehrung/:id', component: AuffuehrungDetailComponent},
    { path: 'auffuehrung/:id/reservieren', component: ReservierungFormComponent},
    { path: 'auffuehrung/:id/buchen', component: BuchungFormComponent},
    { path: 'reservierung', component: ReservierungSucheComponent},

    { path: 'admin', component: AdminDashboardComponent },
    { path: 'admin/kinosaal', component: AdminKinosaalListComponent },
    { path: 'admin/kinosaal/neuerSaal', component: AdminKinosaalFormComponent},
    { path: 'admin/statistik', component: AdminStastistikComponent},
    { path: 'admin/statistik/auffuehrungen', component: AdminStatistikAuffuehrungComponent},
    { path: 'admin/statistik/filme', component: AdminStatistikFilmComponent},
    { path: 'admin/auffuehrung', component: AdminAuffuehrungListComponent},
    { path: 'admin/auffuehrung/neueAuffuehrung', component: AdminAuffuehrungFormComponent},
    { path: 'admin/filme', component: AdminFilmListComponent},
    { path: 'admin/filme/neuerFilm', component: AdminFilmFormComponent},
];
