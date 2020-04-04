import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISurveillant } from 'app/shared/model/surveillant.model';
import { SurveillantService } from './surveillant.service';
import { SurveillantDeleteDialogComponent } from './surveillant-delete-dialog.component';

@Component({
  selector: 'jhi-surveillant',
  templateUrl: './surveillant.component.html'
})
export class SurveillantComponent implements OnInit, OnDestroy {
  surveillants?: ISurveillant[];
  eventSubscriber?: Subscription;

  constructor(
    protected surveillantService: SurveillantService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.surveillantService.query().subscribe((res: HttpResponse<ISurveillant[]>) => (this.surveillants = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSurveillants();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISurveillant): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSurveillants(): void {
    this.eventSubscriber = this.eventManager.subscribe('surveillantListModification', () => this.loadAll());
  }

  delete(surveillant: ISurveillant): void {
    const modalRef = this.modalService.open(SurveillantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.surveillant = surveillant;
  }
}
