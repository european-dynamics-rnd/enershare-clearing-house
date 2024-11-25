import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BidsAcceptedComponent } from './bids-accepted.component';

describe('BidsAcceptedComponent', () => {
  let component: BidsAcceptedComponent;
  let fixture: ComponentFixture<BidsAcceptedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BidsAcceptedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BidsAcceptedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
