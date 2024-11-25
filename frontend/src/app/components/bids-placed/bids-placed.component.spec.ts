import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BidsPlacedComponent } from './bids-placed.component';

describe('BidsComponent', () => {
  let component: BidsPlacedComponent;
  let fixture: ComponentFixture<BidsPlacedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BidsPlacedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BidsPlacedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
