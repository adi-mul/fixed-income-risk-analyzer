import { Component, ViewChild } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatPaginatorModule, MatPaginator} from '@angular/material/paginator';
import { FormsModule } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';
import { CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';

import { HeatMapModule } from '@syncfusion/ej2-angular-heatmap';
import { log } from 'console';
@Component({
  selector: 'app-risk-ana',
  standalone: true,
  imports: [MatTableModule,MatSortModule,MatPaginatorModule,MatFormFieldModule,MatInputModule,FormsModule,CommonModule,CanvasJSAngularChartsModule,MatPaginatorModule,MatFormFieldModule,MatInputModule],
  templateUrl: './risk-ana.component.html',
  styleUrl: './risk-ana.component.css',
  template: `<ag-charts
        [options]="options"
    ></ag-charts>
`,
})
export class RiskAnaComponent {

  chart3:any;
  chart2:any;
  chart1:any;
  chart4:any;
  data:any=[];
  data1:any=[];
  data2:any;
  cupon:any=[];
  bond_id:any;
  bond_id_spread:any;
  future_inflation_rate:any;
  future_inflation_rate_spread:any;
  dataPoints:any=[];
  dataPoints1:any=[];
  dataPoints2:any=[];
  inflation_data:any=[];
  dataPoints3:any=[];
  userForm2: any;
  inflation_table_data:any=[];  

  ///////////////////////////////////////////////////////////
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
		name: "Yield Curve",
		dataPoints:this.dataPoints
	  }
  
  ]
	

  }

  getChart3Instance(chart3: object) {
    this.chart3 = chart3;
  }
////////////////////////////////////////////////////
  chartOptions2 ={
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
		name: "Spread Curve",
		dataPoints:this.dataPoints1
	  }

  ] }
  
  getChart2Instance(chart2: object) {
    this.chart2 = chart2;
  }
  /////////////////////////////////////////////////////////
  constructor( private http:HttpClient) {
    
  }
////////////////////////////////////////////////////////////
  chartOptions1 ={
   
    theme:"dark1",
	  backgroundColor:'#1e293b',
	  title: {
		text: "Spread Analysis"
	  },
	 
	  axisY: {
		title: "%"
	  },
    axisX: {
      title: "years"
      },
	  
	  toolTip: {
		shared: true
	  },
	  legend:{
		cursor:"pointer",
		
	  },
	  data: [{
	    type: "column",	
	    name: "year yeild in %",
	    legendText: "yeild in years",
	    showInLegend: true, 
	    dataPoints:this.dataPoints2,
	  }]
  }

  getChart1Instance(chart1: object) {
    this.chart1 = chart1;
  }
///////////////////////////////////////////////////////////////////////
getChart4Instance(chart4: object) {
  this.chart4 = chart4;
}


chartOptions4 ={
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
  name: "value in %",
  dataPoints:this.dataPoints
  }, {
  type: "line",
  showInLegend: true,
  name: "value",
  dataPoints: this.dataPoints3
  },
  

]


}


  get_yield_data(data:any){
    this.dataPoints.length=0;
    this.bond_id=data.bond_id
    this.future_inflation_rate=data.future_inflation_rate;
    
    return  this.http.get(`http://localhost:9191/fi/yield_cal/`+this.bond_id+`/`+this.future_inflation_rate).subscribe(responce=>{
      
    // return  this.http.post("http://localhost:9191/fi/yield_cal/"+this.data).subscribe(responce=>{
    this.data=responce;

    for(let i = 0; i < this.data.length; i++){          
      this.dataPoints.push({x: i, y: Number(this.data[i]) });
    }
    console.log(this.dataPoints);
    
    this.chart3.render();
  })}

  get_spread_data(data1:any){
    this.dataPoints1.length=0;
    this.bond_id_spread=data1.bond_id_spread;
    this.future_inflation_rate_spread=data1.future_inflation_rate_spread;
    
    console.log(this.bond_id_spread, +"     "+this.future_inflation_rate_spread);
    
    return  this.http.get(`http://localhost:9191/fi/spread/`+this.bond_id_spread+`/`+this.future_inflation_rate_spread).subscribe(responce=>{
      
    // return  this.http.post("http://localhost:9191/fi/yield_cal/"+this.data).subscribe(responce=>{
    this.data=responce;

    for(let i = 0; i < this.data.length; i++){          
      this.dataPoints1.push({x: i, y: Number(this.data[i]) });
    }
    console.log(this.dataPoints1);
    
    this.chart2.render();
  })}

  get_spread_bond_data(data2:any){
    this.dataPoints2.length=0;
    this.bond_id_spread=data2.bond_id_spread;

    return  this.http.get(`http://localhost:9191/fi/spread_bond/`+this.bond_id_spread).subscribe(responce=>{

    this.data=responce;

    // for(let i = 0; i < this.data.length; i++){          
    //   this.dataPoints2.push({x: i, y: Number(this.data[i]) });
      
    // }

     this.dataPoints2.push({x: 0, y: Number(this.data[0]) });
    this.dataPoints2.push({x: 1, y: Number(this.data[1]) });
    this.dataPoints2.push({x: 3, y: Number(this.data[2]) });
    this.dataPoints2.push({x: 5, y: Number(this.data[3]) });
    this.dataPoints2.push({x: 7, y: Number(this.data[4]) });
    this.dataPoints2.push({x: 10, y: Number(this.data[5]) });
    this.dataPoints2.push({x: 20, y: Number(this.data[6]) });
    this.dataPoints2.push({x: 30, y: Number(this.data[7]) });
    this.chart1.render();
  })}

  ngOnInit(): void {
    this.http.get("http://localhost:9191/fi/get_inflation_data").subscribe(data=>{
     this.inflation_data=data
     for(let i = 0; i < this.inflation_data.length; i++){          
       this.dataPoints3.push({x: new Number(this.inflation_data[i].id), y: Number(this.inflation_data[i].value) });
     }    


   })
     
 }
 
  dataSource:any;
    @ViewChild(MatPaginator) paginator !:MatPaginator
  

 ngAfterViewInit():void{
  this.http.get("http://localhost:9191/fi/get_inflation_data").subscribe(data=>{
    this.inflation_table_data=data
    this.dataSource= new MatTableDataSource(this.inflation_table_data)
    this.dataSource.paginator=this.paginator
})}



doFilter = (event:Event) => {
  const Filtervalue =(event.target as HTMLInputElement).value
  this.dataSource.filter = Filtervalue.trim().toLocaleLowerCase();
}

displayedColumns = ['id','year','period','value'];

 
 
}
