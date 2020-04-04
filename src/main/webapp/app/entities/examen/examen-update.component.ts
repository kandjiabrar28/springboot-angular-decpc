import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExamen, Examen } from 'app/shared/model/examen.model';
import { ExamenService } from './examen.service';
import { ITour } from 'app/shared/model/tour.model';
import { TourService } from 'app/entities/tour/tour.service';
import { IJury } from 'app/shared/model/jury.model';
import { JuryService } from 'app/entities/jury/jury.service';

type SelectableEntity = ITour | IJury;

@Component({
  selector: 'jhi-examen-update',
  templateUrl: './examen-update.component.html'
})
export class ExamenUpdateComponent implements OnInit {
  isSaving = false;
  tours: ITour[] = [];
  juries: IJury[] = [];
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    nomexamen: [],
    dateCreation: [],
    dateModification: [],
    tour: [],
    juries: []
  });

  constructor(
    protected examenService: ExamenService,
    protected tourService: TourService,
    protected juryService: JuryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examen }) => {
      this.updateForm(examen);

      this.tourService.query().subscribe((res: HttpResponse<ITour[]>) => (this.tours = res.body || []));

      this.juryService.query().subscribe((res: HttpResponse<IJury[]>) => (this.juries = res.body || []));
    });
  }

  updateForm(examen: IExamen): void {
    this.editForm.patchValue({
      id: examen.id,
      nomexamen: examen.nomexamen,
      dateCreation: examen.dateCreation,
      dateModification: examen.dateModification,
      tour: examen.tour,
      juries: examen.juries
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examen = this.createFromForm();
    if (examen.id !== undefined) {
      this.subscribeToSaveResponse(this.examenService.update(examen));
    } else {
      this.subscribeToSaveResponse(this.examenService.create(examen));
    }
  }

  private createFromForm(): IExamen {
    return {
      ...new Examen(),
      id: this.editForm.get(['id'])!.value,
      nomexamen: this.editForm.get(['nomexamen'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      tour: this.editForm.get(['tour'])!.value,
      juries: this.editForm.get(['juries'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamen>>): void {
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

  getSelected(selectedVals: IJury[], option: IJury): IJury {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
