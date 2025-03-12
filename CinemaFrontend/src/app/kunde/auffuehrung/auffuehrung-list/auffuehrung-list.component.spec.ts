import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuffuehrungListComponent } from './auffuehrung-list.component';

describe('AuffuehrungListComponent', () => {
  let component: AuffuehrungListComponent;
  let fixture: ComponentFixture<AuffuehrungListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuffuehrungListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuffuehrungListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
