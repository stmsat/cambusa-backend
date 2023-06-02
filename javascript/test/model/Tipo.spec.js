/*
 * cambusa-controller
 * Servizio per la tracciatura delle scadenze dei prodotti
 *
 * OpenAPI spec version: 0.0.4
 * Contact: stmsat@gmail.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 *
 * Swagger Codegen version: 3.0.42
 *
 * Do not edit the class manually.
 *
 */
(function(root, factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD.
    define(['expect.js', '../../src/index'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS-like environments that support module.exports, like Node.
    factory(require('expect.js'), require('../../src/index'));
  } else {
    // Browser globals (root is window)
    factory(root.expect, root.CambusaController);
  }
}(this, function(expect, CambusaController) {
  'use strict';

  var instance;

  describe('(package)', function() {
    describe('Tipo', function() {
      beforeEach(function() {
        instance = new CambusaController.Tipo();
      });

      it('should create an instance of Tipo', function() {
        // TODO: update the code to test Tipo
        expect(instance).to.be.a(CambusaController.Tipo);
      });

      it('should have the property id (base name: "id")', function() {
        // TODO: update the code to test the property id
        expect(instance).to.have.property('id');
        // expect(instance.id).to.be(expectedValueLiteral);
      });

      it('should have the property name (base name: "name")', function() {
        // TODO: update the code to test the property name
        expect(instance).to.have.property('name');
        // expect(instance.name).to.be(expectedValueLiteral);
      });

      it('should have the property dataScadenzaPreferibile (base name: "dataScadenzaPreferibile")', function() {
        // TODO: update the code to test the property dataScadenzaPreferibile
        expect(instance).to.have.property('dataScadenzaPreferibile');
        // expect(instance.dataScadenzaPreferibile).to.be(expectedValueLiteral);
      });

      it('should have the property giorniValiditaDopoScadenza (base name: "giorniValiditaDopoScadenza")', function() {
        // TODO: update the code to test the property giorniValiditaDopoScadenza
        expect(instance).to.have.property('giorniValiditaDopoScadenza');
        // expect(instance.giorniValiditaDopoScadenza).to.be(expectedValueLiteral);
      });

      it('should have the property apribile (base name: "apribile")', function() {
        // TODO: update the code to test the property apribile
        expect(instance).to.have.property('apribile');
        // expect(instance.apribile).to.be(expectedValueLiteral);
      });

      it('should have the property giorniValiditaDopoApertura (base name: "giorniValiditaDopoApertura")', function() {
        // TODO: update the code to test the property giorniValiditaDopoApertura
        expect(instance).to.have.property('giorniValiditaDopoApertura');
        // expect(instance.giorniValiditaDopoApertura).to.be(expectedValueLiteral);
      });

    });
  });

}));
