import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BuchungService } from 'src/app/shared/services/buchung.service';

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
    private buchungService: BuchungService
  ) {
    this.buchungForm = this.fb.group({
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
      this.buchungForm.patchValue({ sitzplaetze });
    });
  }

  onSubmit() {
    if (this.buchungForm.valid) {
      console.log('Buchung:', this.buchungForm.value);
      this.buchungService.createBuchung(this.buchungForm.value).subscribe(
        response => console.log('Buchung erfolgreich:', response),
        error => console.error('Fehler bei der Buchung:', error)
      );
    }
  }
}
