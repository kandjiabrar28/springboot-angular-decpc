import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJury, Jury } from 'app/shared/model/jury.model';
import { JuryService } from './jury.service';
import { IExamen } from 'app/shared/model/examen.model';
import { ExamenService } from 'app/entities/examen/examen.service';

@Component({
  selector: 'jhi-jury-update',
  templateUrl: './jury-update.component.html'
})
export class JuryUpdateComponent implements OnInit {
  isSaving = false;
  examen: IExamen[] = [];

  editForm = this.fb.group({
    id: [],
    numjury: [],
    examen: []
  });

  constructor(
    protected juryService: JuryService,
    protected examenService: ExamenService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jury }) => {
      this.updateForm(jury);

      this.examenService.query().subscribe((res: HttpResponse<IExamen[]>) => (this.examen = res.body || []));
    });
  }

  updateForm(jury: IJury): void {
    this.editForm.patchValue({
      id: jury.id,
      numjury: jury.numjury,
      examen: jury.examen
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jury = this.createFromForm();
    if (jury.id !== undefined) {
      this.subscribeToSaveResponse(this.juryService.update(jury));
    } else {
      this.subscribeToSaveResponse(this.juryService.create(jury));
    }
  }

  private createFromForm(): IJury {
    return {
      ...new Jury(),
      id: this.editForm.get(['id'])!.value,
      numjury: this.editForm.get(['numjury'])!.value,
      examen: this.editForm.get(['examen'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJury>>): void {
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

  trackById(index: number, item: IExamen): any {
    return item.id;
  }

  getSelected(selectedVals: IExamen[], option: IExamen): IExamen {
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
