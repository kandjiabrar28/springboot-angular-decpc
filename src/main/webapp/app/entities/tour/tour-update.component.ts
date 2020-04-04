import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITour, Tour } from 'app/shared/model/tour.model';
import { TourService } from './tour.service';
import { IMatiere } from 'app/shared/model/matiere.model';
import { MatiereService } from 'app/entities/matiere/matiere.service';
import { ITAnonym } from 'app/shared/model/t-anonym.model';
import { TAnonymService } from 'app/entities/t-anonym/t-anonym.service';

type SelectableEntity = IMatiere | ITAnonym;

@Component({
  selector: 'jhi-tour-update',
  templateUrl: './tour-update.component.html'
})
export class TourUpdateComponent implements OnInit {
  isSaving = false;
  matieres: IMatiere[] = [];
  tanonyms: ITAnonym[] = [];
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    numtour: [],
    dateCreation: [],
    dateModification: [],
    matiere: [],
    tAnonym: []
  });

  constructor(
    protected tourService: TourService,
    protected matiereService: MatiereService,
    protected tAnonymService: TAnonymService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tour }) => {
      this.updateForm(tour);

      this.matiereService.query().subscribe((res: HttpResponse<IMatiere[]>) => (this.matieres = res.body || []));

      this.tAnonymService.query().subscribe((res: HttpResponse<ITAnonym[]>) => (this.tanonyms = res.body || []));
    });
  }

  updateForm(tour: ITour): void {
    this.editForm.patchValue({
      id: tour.id,
      numtour: tour.numtour,
      dateCreation: tour.dateCreation,
      dateModification: tour.dateModification,
      matiere: tour.matiere,
      tAnonym: tour.tAnonym
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tour = this.createFromForm();
    if (tour.id !== undefined) {
      this.subscribeToSaveResponse(this.tourService.update(tour));
    } else {
      this.subscribeToSaveResponse(this.tourService.create(tour));
    }
  }

  private createFromForm(): ITour {
    return {
      ...new Tour(),
      id: this.editForm.get(['id'])!.value,
      numtour: this.editForm.get(['numtour'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      matiere: this.editForm.get(['matiere'])!.value,
      tAnonym: this.editForm.get(['tAnonym'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITour>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
