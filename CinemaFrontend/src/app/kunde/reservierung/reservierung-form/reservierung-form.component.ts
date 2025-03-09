import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Film } from 'src/app/shared/models/film.model';
import { Auffuehrung } from 'src/app/shared/models/auffuehrung.model';
import { Kinosaal } from 'src/app/shared/models/kinosaal.model';
import { ReservierungService } from 'src/app/shared/services/reservierung.service';

@Component({
  selector: 'app-reservierung-form',
  imports: [ReactiveFormsModule],
  templateUrl: './reservierung-form.component.html',
  styleUrls: ['./reservierung-form.component.css']
})
export class ReservierungFormComponent implements OnInit {
  reservierungForm: FormGroup;

  constructor(
    private fb: FormBuilder, 
    private route: ActivatedRoute,
    private reservierungService: ReservierungService
  ) {
    this.reservierungForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      sitzplaetze: [[], Validators.required],
      film: [null, Validators.required],
      auffuehrung: [null, Validators.required],
      kinosaal: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const sitzplaetze = params['sitzplaetze'] ? params['sitzplaetze'].split(',') : [];
      this.reservierungForm.patchValue({ sitzplaetze });
    });
  }

  onSubmit() {
    if (this.reservierungForm.valid) {
      console.log('Reservierung:', this.reservierungForm.value);
      this.reservierungService.createReservierung(this.reservierungForm.value).subscribe(
        response => console.log('Reservierung erfolgreich:', response),
        error => console.error('Fehler bei der Reservierung:', error)
      );
    }
  }
}