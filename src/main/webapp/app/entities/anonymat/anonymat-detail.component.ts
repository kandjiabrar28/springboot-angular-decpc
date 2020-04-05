import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnonymat } from 'app/shared/model/anonymat.model';

@Component({
  selector: 'jhi-anonymat-detail',
  templateUrl: './anonymat-detail.component.html'
})
export class AnonymatDetailComponent implements OnInit {
  anonymat: IAnonymat | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anonymat }) => (this.anonymat = anonymat));
  }

  previousState(): void {
    window.history.back();
  }
}
