import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EgressLogsComponent } from './egress-logs.component';

describe('EgressLogsComponent', () => {
  let component: EgressLogsComponent;
  let fixture: ComponentFixture<EgressLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EgressLogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EgressLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
