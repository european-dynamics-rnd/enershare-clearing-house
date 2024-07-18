import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IngressLogsComponent } from './ingress-logs.component';

describe('IngressLogsComponent', () => {
  let component: IngressLogsComponent;
  let fixture: ComponentFixture<IngressLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IngressLogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(IngressLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
