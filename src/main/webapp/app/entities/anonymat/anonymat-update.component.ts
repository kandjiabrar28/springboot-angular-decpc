import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnonymat, Anonymat } from 'app/shared/model/anonymat.model';
import { AnonymatService } from './anonymat.service';
import { ICandidat } from 'app/shared/model/candidat.model';
import { CandidatService } from 'app/entities/candidat/candidat.service';
import { ITour } from 'app/shared/model/tour.model';
import { TourService } from 'app/entities/tour/tour.service';

type SelectableEntity = ICandidat | ITour;

@Component({
  selector: 'jhi-anonymat-update',
  templateUrl: './anonymat-update.component.html'
})
export class AnonymatUpdateComponent implements OnInit {
  isSaving = false;
  candidats: ICandidat[] = [];
  tours: ITour[] = [];

  editForm = this.fb.group({
    id: [],
    numanoymat: [],
    candidat: [],
    tour: []
  });

  constructor(
    protected anonymatService: AnonymatService,
    protected candidatService: CandidatService,
    protected tourService: TourService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anonymat }) => {
      this.updateForm(anonymat);

      this.candidatService.query().subscribe((res: HttpResponse<ICandidat[]>) => (this.candidats = res.body || []));

      this.tourService.query().subscribe((res: HttpResponse<ITour[]>) => (this.tours = res.body || []));
    });
  }

  updateForm(anonymat: IAnonymat): void {
    this.editForm.patchValue({
      id: anonymat.id,
      numanoymat: anonymat.numanoymat,
      candidat: anonymat.candidat,
      tour: anonymat.tour
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anonymat = this.createFromForm();
    if (anonymat.id !== undefined) {
      this.subscribeToSaveResponse(this.anonymatService.update(anonymat));
    } else {
      this.subscribeToSaveResponse(this.anonymatService.create(anonymat));
    }
  }

  private createFromForm(): IAnonymat {
    return {
      ...new Anonymat(),
      id: this.editForm.get(['id'])!.value,
      numanoymat: this.editForm.get(['numanoymat'])!.value,
      candidat: this.editForm.get(['candidat'])!.value,
      tour: this.editForm.get(['tour'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnonymat>>): void {
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
