import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPVSurveillance, PVSurveillance } from 'app/shared/model/pv-surveillance.model';
import { PVSurveillanceService } from './pv-surveillance.service';
import { ISalle } from 'app/shared/model/salle.model';
import { SalleService } from 'app/entities/salle/salle.service';
import { ISurveillant } from 'app/shared/model/surveillant.model';
import { SurveillantService } from 'app/entities/surveillant/surveillant.service';

type SelectableEntity = ISalle | ISurveillant;

@Component({
  selector: 'jhi-pv-surveillance-update',
  templateUrl: './pv-surveillance-update.component.html'
})
export class PVSurveillanceUpdateComponent implements OnInit {
  isSaving = false;
  salles: ISalle[] = [];
  surveillants: ISurveillant[] = [];
  datesurvDp: any;

  editForm = this.fb.group({
    id: [],
    epreuve: [],
    heureDeb: [],
    heureFin: [],
    datesurv: [],
    salle: [],
    surveillant: []
  });

  constructor(
    protected pVSurveillanceService: PVSurveillanceService,
    protected salleService: SalleService,
    protected surveillantService: SurveillantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pVSurveillance }) => {
      if (!pVSurveillance.id) {
        const today = moment().startOf('day');
        pVSurveillance.heureDeb = today;
        pVSurveillance.heureFin = today;
      }

      this.updateForm(pVSurveillance);

      this.salleService.query().subscribe((res: HttpResponse<ISalle[]>) => (this.salles = res.body || []));

      this.surveillantService.query().subscribe((res: HttpResponse<ISurveillant[]>) => (this.surveillants = res.body || []));
    });
  }

  updateForm(pVSurveillance: IPVSurveillance): void {
    this.editForm.patchValue({
      id: pVSurveillance.id,
      epreuve: pVSurveillance.epreuve,
      heureDeb: pVSurveillance.heureDeb ? pVSurveillance.heureDeb.format(DATE_TIME_FORMAT) : null,
      heureFin: pVSurveillance.heureFin ? pVSurveillance.heureFin.format(DATE_TIME_FORMAT) : null,
      datesurv: pVSurveillance.datesurv,
      salle: pVSurveillance.salle,
      surveillant: pVSurveillance.surveillant
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pVSurveillance = this.createFromForm();
    if (pVSurveillance.id !== undefined) {
      this.subscribeToSaveResponse(this.pVSurveillanceService.update(pVSurveillance));
    } else {
      this.subscribeToSaveResponse(this.pVSurveillanceService.create(pVSurveillance));
    }
  }

  private createFromForm(): IPVSurveillance {
    return {
      ...new PVSurveillance(),
      id: this.editForm.get(['id'])!.value,
      epreuve: this.editForm.get(['epreuve'])!.value,
      heureDeb: this.editForm.get(['heureDeb'])!.value ? moment(this.editForm.get(['heureDeb'])!.value, DATE_TIME_FORMAT) : undefined,
      heureFin: this.editForm.get(['heureFin'])!.value ? moment(this.editForm.get(['heureFin'])!.value, DATE_TIME_FORMAT) : undefined,
      datesurv: this.editForm.get(['datesurv'])!.value,
      salle: this.editForm.get(['salle'])!.value,
      surveillant: this.editForm.get(['surveillant'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPVSurveillance>>): void {
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
