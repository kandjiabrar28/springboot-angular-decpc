import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISalle, Salle } from 'app/shared/model/salle.model';
import { SalleService } from './salle.service';
import { IPVSurveillance } from 'app/shared/model/pv-surveillance.model';
import { PVSurveillanceService } from 'app/entities/pv-surveillance/pv-surveillance.service';
import { ISurveillant } from 'app/shared/model/surveillant.model';
import { SurveillantService } from 'app/entities/surveillant/surveillant.service';
import { ICentre } from 'app/shared/model/centre.model';
import { CentreService } from 'app/entities/centre/centre.service';

type SelectableEntity = IPVSurveillance | ISurveillant | ICentre;

@Component({
  selector: 'jhi-salle-update',
  templateUrl: './salle-update.component.html'
})
export class SalleUpdateComponent implements OnInit {
  isSaving = false;
  pvsurveillances: IPVSurveillance[] = [];
  surveillants: ISurveillant[] = [];
  centres: ICentre[] = [];
  dateCreationDp: any;
  dateModificationDp: any;

  editForm = this.fb.group({
    id: [],
    numsalle: [],
    nomsalle: [],
    dateCreation: [],
    dateModification: [],
    pvsurveillance: [],
    surveillants: [],
    centre: []
  });

  constructor(
    protected salleService: SalleService,
    protected pVSurveillanceService: PVSurveillanceService,
    protected surveillantService: SurveillantService,
    protected centreService: CentreService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ salle }) => {
      this.updateForm(salle);

      this.pVSurveillanceService.query().subscribe((res: HttpResponse<IPVSurveillance[]>) => (this.pvsurveillances = res.body || []));

      this.surveillantService.query().subscribe((res: HttpResponse<ISurveillant[]>) => (this.surveillants = res.body || []));

      this.centreService.query().subscribe((res: HttpResponse<ICentre[]>) => (this.centres = res.body || []));
    });
  }

  updateForm(salle: ISalle): void {
    this.editForm.patchValue({
      id: salle.id,
      numsalle: salle.numsalle,
      nomsalle: salle.nomsalle,
      dateCreation: salle.dateCreation,
      dateModification: salle.dateModification,
      pvsurveillance: salle.pvsurveillance,
      surveillants: salle.surveillants,
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
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      dateModification: this.editForm.get(['dateModification'])!.value,
      pvsurveillance: this.editForm.get(['pvsurveillance'])!.value,
      surveillants: this.editForm.get(['surveillants'])!.value,
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: ISurveillant[], option: ISurveillant): ISurveillant {
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
