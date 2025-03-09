import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FooterComponent } from './kunde/shared/footer/footer.component';
import { HeaderComponent } from './kunde/shared/header/header.component';

@Component({
  imports: [FooterComponent, HeaderComponent, RouterModule],
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'CinemaFrontend';
}
