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
import {ApiClient} from '../ApiClient';

/**
 * The Tipo model module.
 * @module model/Tipo
 * @version 0.0.4
 */
export class Tipo {
  /**
   * Constructs a new <code>Tipo</code>.
   * @alias module:model/Tipo
   * @class
   * @param name {String} 
   * @param dataScadenzaPreferibile {Boolean} 
   */
  constructor(name, dataScadenzaPreferibile) {
    this.name = name;
    this.dataScadenzaPreferibile = dataScadenzaPreferibile;
  }

  /**
   * Constructs a <code>Tipo</code> from a plain JavaScript object, optionally creating a new instance.
   * Copies all relevant properties from <code>data</code> to <code>obj</code> if supplied or a new instance if not.
   * @param {Object} data The plain JavaScript object bearing properties of interest.
   * @param {module:model/Tipo} obj Optional instance to populate.
   * @return {module:model/Tipo} The populated <code>Tipo</code> instance.
   */
  static constructFromObject(data, obj) {
    if (data) {
      obj = obj || new Tipo();
      if (data.hasOwnProperty('id'))
        obj.id = ApiClient.convertToType(data['id'], 'String');
      if (data.hasOwnProperty('name'))
        obj.name = ApiClient.convertToType(data['name'], 'String');
      if (data.hasOwnProperty('dataScadenzaPreferibile'))
        obj.dataScadenzaPreferibile = ApiClient.convertToType(data['dataScadenzaPreferibile'], 'Boolean');
      if (data.hasOwnProperty('giorniValiditaDopoScadenza'))
        obj.giorniValiditaDopoScadenza = ApiClient.convertToType(data['giorniValiditaDopoScadenza'], 'Number');
      if (data.hasOwnProperty('apribile'))
        obj.apribile = ApiClient.convertToType(data['apribile'], 'Boolean');
      if (data.hasOwnProperty('giorniValiditaDopoApertura'))
        obj.giorniValiditaDopoApertura = ApiClient.convertToType(data['giorniValiditaDopoApertura'], 'Number');
    }
    return obj;
  }
}

/**
 * @member {String} id
 */
Tipo.prototype.id = undefined;

/**
 * @member {String} name
 */
Tipo.prototype.name = undefined;

/**
 * @member {Boolean} dataScadenzaPreferibile
 */
Tipo.prototype.dataScadenzaPreferibile = undefined;

/**
 * @member {Number} giorniValiditaDopoScadenza
 */
Tipo.prototype.giorniValiditaDopoScadenza = undefined;

/**
 * @member {Boolean} apribile
 */
Tipo.prototype.apribile = undefined;

/**
 * @member {Number} giorniValiditaDopoApertura
 */
Tipo.prototype.giorniValiditaDopoApertura = undefined;

