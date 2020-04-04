import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPVSurveillance } from 'app/shared/model/pv-surveillance.model';

@Component({
  selector: 'jhi-pv-surveillance-detail',
  templateUrl: './pv-surveillance-detail.component.html'
})
export class PVSurveillanceDetailComponent implements OnInit {
  pVSurveillance: IPVSurveillance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pVSurveillance }) => (this.pVSurveillance = pVSurveillance));
  }

  previousState(): void {
    window.history.back();
  }
}
