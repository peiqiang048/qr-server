package com.cnc.qr.api.pc.resource;

import com.cnc.qr.common.model.CommonOutputResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 飲み放題編集確定アウトプットリソース.
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class RegistBuffetOutputResource extends CommonOutputResource {


}
