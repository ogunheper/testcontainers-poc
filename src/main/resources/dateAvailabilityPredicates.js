function() {
    const timestamp = new ISODate("__QUERY__").getTime();
    switch (this.productSalesDates._class) {
        case 'com.ogunheper.testcontainers.demo.model.DiscreteProductSalesDates':
            return this.productSalesDates.dates.some(function(value) { return value.getTime() === timestamp; });
        case 'com.ogunheper.testcontainers.demo.model.ContinuousProductSalesDates':
            return this.productSalesDates.startDate.getTime() <= timestamp && timestamp <= this.productSalesDates.endDate.getTime();
        default:
            throw 'Unexpected type ' + this.productSalesDates._class;
    }
}