import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJury } from 'app/shared/model/jury.model';
import { JuryService } from './jury.service';
import { JuryDeleteDialogComponent } from './jury-delete-dialog.component';

@Component({
  selector: 'jhi-jury',
  templateUrl: './jury.component.html'
})
export class JuryComponent implements OnInit, OnDestroy {
  juries?: IJury[];
  eventSubscriber?: Subscription;

  constructor(protected juryService: JuryService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.juryService.query().subscribe((res: HttpResponse<IJury[]>) => (this.juries = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJuries();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJury): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJuries(): void {
    this.eventSubscriber = this.eventManager.subscribe('juryListModification', () => this.loadAll());
  }

  delete(jury: IJury): void {
    const modalRef = this.modalService.open(JuryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jury = jury;
  }
}
