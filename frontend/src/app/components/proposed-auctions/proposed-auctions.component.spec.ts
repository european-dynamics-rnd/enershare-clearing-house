import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProposedAuctionsComponent } from './proposed-auctions.component';

describe('AuctionsComponent', () => {
  let component: ProposedAuctionsComponent;
  let fixture: ComponentFixture<ProposedAuctionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProposedAuctionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProposedAuctionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
