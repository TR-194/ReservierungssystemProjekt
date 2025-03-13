import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { KafkaService } from 'src/app/shared/services/kafka.service';
import { Sitzplatz } from 'src/app/shared/models/sitzplatz.model';

@Component({
  selector: 'app-reservierung-form',
  imports: [ReactiveFormsModule],
  templateUrl: './reservierung-form.component.html',
  styleUrls: ['./reservierung-form.component.css']
})
export class ReservierungFormComponent implements OnInit {
  reservierungForm: FormGroup;
  verfügbareSitzplätze: Sitzplatz[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private kafkaService: KafkaService
  ) {
    this.reservierungForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      sitzplatzIds: [[], Validators.required], // Statt sitzplaetze -> sitzplatzIds
      auffuehrungId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    // Daten aus Query-Params holen
    this.route.queryParams.subscribe(params => {
      const sitzplatzIds = params['sitzplaetze'] ? params['sitzplaetze'].split(',').map(Number) : [];
      this.reservierungForm.patchValue({ sitzplatzIds });

      // Falls `auffuehrungId` aus den Parametern kommt, setzen
      if (params['auffuehrungId']) {
        this.reservierungForm.patchValue({ auffuehrungId: Number(params['auffuehrungId']) });
      }
    });

    // Verfügbare Sitzplätze aus Kafka abrufen
    this.ladeVerfügbareSitzplätze();
  }

  ladeVerfügbareSitzplätze(): void {
    this.kafkaService.sendRequest<Sitzplatz[]>('sitzplatzGetVerfügbare')
      .subscribe(sitzplaetze => {
        this.verfügbareSitzplätze = sitzplaetze;
      });
  }

  onSubmit() {
    if (this.reservierungForm.valid) {
      const reservierung = this.reservierungForm.value;
      console.log('Reservierung:', reservierung);

      // Reservierung über Kafka senden
      this.kafkaService.sendRequest('reservierungCreate', reservierung)
        .subscribe(
          response => console.log('Reservierung erfolgreich:', response),
          error => console.error('Fehler bei der Reservierung:', error)
        );
    }
  }
}
