import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPVSurveillance } from 'app/shared/model/pv-surveillance.model';
import { PVSurveillanceService } from './pv-surveillance.service';
import { PVSurveillanceDeleteDialogComponent } from './pv-surveillance-delete-dialog.component';

@Component({
  selector: 'jhi-pv-surveillance',
  templateUrl: './pv-surveillance.component.html'
})
export class PVSurveillanceComponent implements OnInit, OnDestroy {
  pVSurveillances?: IPVSurveillance[];
  eventSubscriber?: Subscription;

  constructor(
    protected pVSurveillanceService: PVSurveillanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.pVSurveillanceService.query().subscribe((res: HttpResponse<IPVSurveillance[]>) => (this.pVSurveillances = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPVSurveillances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPVSurveillance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPVSurveillances(): void {
    this.eventSubscriber = this.eventManager.subscribe('pVSurveillanceListModification', () => this.loadAll());
  }

  delete(pVSurveillance: IPVSurveillance): void {
    const modalRef = this.modalService.open(PVSurveillanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pVSurveillance = pVSurveillance;
  }
}
