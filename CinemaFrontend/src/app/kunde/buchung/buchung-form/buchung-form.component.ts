import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { KafkaService } from 'src/app/shared/services/kafka.service';

@Component({
  selector: 'app-buchung-form',
  imports: [ReactiveFormsModule],
  templateUrl: './buchung-form.component.html',
  styleUrls: ['./buchung-form.component.css']
})
export class BuchungFormComponent implements OnInit {
  buchungForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private kafkaService: KafkaService
  ) {
    this.buchungForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      sitzplatzIds: [[], Validators.required],
      auffuehrungId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const sitzplatzIds = params['sitzplaetze'] ? params['sitzplaetze'].split(',').map(Number) : [];
      const auffuehrungId = params['auffuehrungId'] ? Number(params['auffuehrungId']) : null;

      this.buchungForm.patchValue({ sitzplatzIds, auffuehrungId });
    });
  }

  onSubmit() {
    if (this.buchungForm.valid) {
      console.log('Buchung:', this.buchungForm.value);
      this.kafkaService.sendRequest('buchung.create', this.buchungForm.value)
        .subscribe(
          response => console.log('Buchung erfolgreich:', response),
          error => console.error('Fehler bei der Buchung:', error)
        );
    }
  }
}
