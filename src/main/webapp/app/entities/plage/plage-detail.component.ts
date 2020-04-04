import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlage } from 'app/shared/model/plage.model';

@Component({
  selector: 'jhi-plage-detail',
  templateUrl: './plage-detail.component.html'
})
export class PlageDetailComponent implements OnInit {
  plage: IPlage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plage }) => (this.plage = plage));
  }

  previousState(): void {
    window.history.back();
  }
}
