import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISalle, Salle } from 'app/shared/model/salle.model';
import { SalleService } from './salle.service';
import { ICentre } from 'app/shared/model/centre.model';
import { CentreService } from 'app/entities/centre/centre.service';

@Component({
  selector: 'jhi-salle-update',
  templateUrl: './salle-update.component.html'
})
export class SalleUpdateComponent implements OnInit {
  isSaving = false;
  centres: ICentre[] = [];

  editForm = this.fb.group({
    id: [],
    numsalle: [],
    nomsalle: [],
    centre: []
  });

  constructor(
    protected salleService: SalleService,
    protected centreService: CentreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salle }) => {
      this.updateForm(salle);

      this.centreService.query().subscribe((res: HttpResponse<ICentre[]>) => (this.centres = res.body || []));
    });
  }

  updateForm(salle: ISalle): void {
    this.editForm.patchValue({
      id: salle.id,
      numsalle: salle.numsalle,
      nomsalle: salle.nomsalle,
      centre: salle.centre
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const salle = this.createFromForm();
    if (salle.id !== undefined) {
      this.subscribeToSaveResponse(this.salleService.update(salle));
    } else {
      this.subscribeToSaveResponse(this.salleService.create(salle));
    }
  }

  private createFromForm(): ISalle {
    return {
      ...new Salle(),
      id: this.editForm.get(['id'])!.value,
      numsalle: this.editForm.get(['numsalle'])!.value,
      nomsalle: this.editForm.get(['nomsalle'])!.value,
      centre: this.editForm.get(['centre'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISalle>>): void {
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

  trackById(index: number, item: ICentre): any {
    return item.id;
  }
}
