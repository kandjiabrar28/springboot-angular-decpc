import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITour } from 'app/shared/model/tour.model';

@Component({
  selector: 'jhi-tour-detail',
  templateUrl: './tour-detail.component.html'
})
export class TourDetailComponent implements OnInit {
  tour: ITour | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tour }) => (this.tour = tour));
  }

  previousState(): void {
    window.history.back();
  }
}
