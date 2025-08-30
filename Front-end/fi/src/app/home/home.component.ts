import { Component } from '@angular/core';


import { AfterViewInit,OnInit,ViewChild } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule, MatPaginator} from '@angular/material/paginator';

import { MatTableDataSource } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatTableModule,MatSortModule,MatPaginatorModule,MatFormFieldModule,MatInputModule,CanvasJSAngularChartsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  dataPoints:any=[];
  dataPoints1:any = [];
  dataPoints2:any=[];
  dataPoints3:any=[];
  fi: any=[]
  chart3:any;

  ///Data display variable for SOFRINDEX/////

 
  date :any;
  avg_30:any;
  avg_90:any;
  avg_180:any;
  sofr_index_rate: any;


  getChart3Instance(chart3: object) {
    this.chart3 = chart3;
  }


  chartOptions3 ={
    theme:"dark1",
	  backgroundColor:'#1e293b',
	  toolTip: {
		shared: true
	  },
	  legend: {
		cursor: "pointer"
	  },
	  data: [{
		type: "line",
		showInLegend: true,
		name: "Average 30",
		dataPoints:this.dataPoints
	  }, {
		type: "line",
		showInLegend: true,
		name: "Average 90",
		dataPoints: this.dataPoints1
	  },
    {
      type: "line",
      showInLegend: true,
      name: "Average 180",
      dataPoints: this.dataPoints2
      },
      {
        type: "line",
        showInLegend: true,
        name: "SOFR index Value",
        dataPoints: this.dataPoints3
        }
  
  ]
	

  }


  
  constructor( private http:HttpClient){}
   
  dataSource:any;
    @ViewChild(MatPaginator) paginator !:MatPaginator
  
  
  
  ngOnInit(): void {
     this.http.get("http://localhost:9191/fi/avg_sofr").subscribe(data=>{
      this.fi=data
      for(let i = 0; i < this.fi.length; i++){          
        this.dataPoints.push({x: new Number(this.fi[i].id), y: Number(this.fi[i].avg_30) });
        this.dataPoints1.push({x: new Number(this.fi[i].id), y: Number(this.fi[i].avg_90) });
        this.dataPoints2.push({x: new Number(this.fi[i].id), y: Number(this.fi[i].avg_180) });
        this.dataPoints3.push({x: new Number(this.fi[i].id), y: Number(this.fi[i].sofr_index) });
      }    
      this.date=this.fi[0].date;
      this.avg_30=this.fi[0].avg_30;
      this.avg_90=this.fi[0].avg_90;
      this.avg_180=this.fi[0].avg_180;
      this.sofr_index_rate=this.fi[0].sofr_index;

    })
      
  }
  ngAfterViewInit():void{
    this.http.get("http://localhost:9191/fi/avg_sofr").subscribe(data=>{
      this.fi=data
      this.dataSource= new MatTableDataSource(this.fi)
      this.dataSource.paginator=this.paginator
  })}

  

  doFilter = (event:Event) => {
    const Filtervalue =(event.target as HTMLInputElement).value
    this.dataSource.filter = Filtervalue.trim().toLocaleLowerCase();
  }
  
  displayedColumns = ['Date','Rate type','Average 30','Average 90','Average 90','Average 180','Index value'];

}
