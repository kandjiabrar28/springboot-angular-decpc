import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlageCopie } from 'app/shared/model/plage-copie.model';

@Component({
  selector: 'jhi-plage-copie-detail',
  templateUrl: './plage-copie-detail.component.html'
})
export class PlageCopieDetailComponent implements OnInit {
  plageCopie: IPlageCopie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plageCopie }) => (this.plageCopie = plageCopie));
  }

  previousState(): void {
    window.history.back();
  }
}
