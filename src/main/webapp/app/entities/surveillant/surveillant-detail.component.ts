import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISurveillant } from 'app/shared/model/surveillant.model';

@Component({
  selector: 'jhi-surveillant-detail',
  templateUrl: './surveillant-detail.component.html'
})
export class SurveillantDetailComponent implements OnInit {
  surveillant: ISurveillant | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ surveillant }) => (this.surveillant = surveillant));
  }

  previousState(): void {
    window.history.back();
  }
}
