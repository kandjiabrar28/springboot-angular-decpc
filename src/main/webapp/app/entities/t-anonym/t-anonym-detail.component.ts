import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITAnonym } from 'app/shared/model/t-anonym.model';

@Component({
  selector: 'jhi-t-anonym-detail',
  templateUrl: './t-anonym-detail.component.html'
})
export class TAnonymDetailComponent implements OnInit {
  tAnonym: ITAnonym | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tAnonym }) => (this.tAnonym = tAnonym));
  }

  previousState(): void {
    window.history.back();
  }
}
