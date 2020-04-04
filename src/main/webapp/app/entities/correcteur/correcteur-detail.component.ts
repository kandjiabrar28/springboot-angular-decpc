import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICorrecteur } from 'app/shared/model/correcteur.model';

@Component({
  selector: 'jhi-correcteur-detail',
  templateUrl: './correcteur-detail.component.html'
})
export class CorrecteurDetailComponent implements OnInit {
  correcteur: ICorrecteur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ correcteur }) => (this.correcteur = correcteur));
  }

  previousState(): void {
    window.history.back();
  }
}
