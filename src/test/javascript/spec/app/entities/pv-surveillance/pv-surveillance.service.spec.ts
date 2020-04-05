import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PVSurveillanceService } from 'app/entities/pv-surveillance/pv-surveillance.service';
import { IPVSurveillance, PVSurveillance } from 'app/shared/model/pv-surveillance.model';

describe('Service Tests', () => {
  describe('PVSurveillance Service', () => {
    let injector: TestBed;
    let service: PVSurveillanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IPVSurveillance;
    let expectedResult: IPVSurveillance | IPVSurveillance[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PVSurveillanceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PVSurveillance(0, 'AAAAAAA', currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            heureDeb: currentDate.format(DATE_TIME_FORMAT),
            heureFin: currentDate.format(DATE_TIME_FORMAT),
            datesurv: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PVSurveillance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            heureDeb: currentDate.format(DATE_TIME_FORMAT),
            heureFin: currentDate.format(DATE_TIME_FORMAT),
            datesurv: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDeb: currentDate,
            heureFin: currentDate,
            datesurv: currentDate
          },
          returnedFromService
        );

        service.create(new PVSurveillance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PVSurveillance', () => {
        const returnedFromService = Object.assign(
          {
            epreuve: 'BBBBBB',
            heureDeb: currentDate.format(DATE_TIME_FORMAT),
            heureFin: currentDate.format(DATE_TIME_FORMAT),
            datesurv: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDeb: currentDate,
            heureFin: currentDate,
            datesurv: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PVSurveillance', () => {
        const returnedFromService = Object.assign(
          {
            epreuve: 'BBBBBB',
            heureDeb: currentDate.format(DATE_TIME_FORMAT),
            heureFin: currentDate.format(DATE_TIME_FORMAT),
            datesurv: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            heureDeb: currentDate,
            heureFin: currentDate,
            datesurv: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PVSurveillance', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
