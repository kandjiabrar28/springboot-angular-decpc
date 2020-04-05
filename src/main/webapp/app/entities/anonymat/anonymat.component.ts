import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnonymat } from 'app/shared/model/anonymat.model';
import { AnonymatService } from './anonymat.service';
import { AnonymatDeleteDialogComponent } from './anonymat-delete-dialog.component';

@Component({
  selector: 'jhi-anonymat',
  templateUrl: './anonymat.component.html'
})
export class AnonymatComponent implements OnInit, OnDestroy {
  anonymats?: IAnonymat[];
  eventSubscriber?: Subscription;

  constructor(protected anonymatService: AnonymatService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.anonymatService.query().subscribe((res: HttpResponse<IAnonymat[]>) => (this.anonymats = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAnonymats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAnonymat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAnonymats(): void {
    this.eventSubscriber = this.eventManager.subscribe('anonymatListModification', () => this.loadAll());
  }

  delete(anonymat: IAnonymat): void {
    const modalRef = this.modalService.open(AnonymatDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.anonymat = anonymat;
  }
}
