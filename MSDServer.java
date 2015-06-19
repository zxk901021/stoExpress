package com.cneop.msd;

import com.cneop.sto.common.GlobalParas;
import com.cneop.sto.model.Enums.EDownError;
import com.cneop.sto.model.Enums.ERegResponse;
import com.cneop.sto.model.Enums.RequestOp; 
import com.cneop.util.StrUtil;
import com.cneop.util.zip.GZipUtil;

import android.content.Context;

public class MSDServer {

	private TransferParam tParam;// �������
	private WCFClient wcfClient;
	StrUtil strUtil;

	private static MSDServer msdServer;

	public static MSDServer getInstance(Context context) {
		if (msdServer == null) {
			msdServer = new MSDServer(context);
		}
		return msdServer;
	}

	private MSDServer(Context context) {
		wcfClient = new WCFClient();
		strUtil = new StrUtil();
	}

	/**
	 * ���÷�������ַ
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		wcfClient.setUrl(url);
	}

	/**
	 * ���ô������
	 * 
	 * @param tp
	 */
	public void setParam(TransferParam tp) {
		this.tParam = tp;
	}

	/**
	 * ���ô������
	 * 
	 * @param stationId
	 * @param enterpriseId
	 * @param padId
	 * @param version
	 */
	public void SetParam(String stationId, String enterpriseId, String padId,
			String version) {
		TransferParam t = new TransferParam();
		t.setStationID(stationId);
		t.setEnterpriseID(enterpriseId);
		t.setPdaId(padId);
		t.setVersion(version);
		t.setDefaultLogic(false);
		t.setCompress(true);
		setParam(t);
	}

	/**
	 * ��÷�����ʱ��
	 * 
	 * @return
	 */
	public String getServerTime() {
		// wcfClient.setUrl("http://192.168.1.157:8086/PDATransferWS.svc");
		return wcfClient.getServerTime();
	}

	/**
	 * ע��:0�����쳣 1 δ���ò��� 2ע��ɹ� 3��ע�� 4����
	 * 
	 * @return
	 * @throws Exception
	 */
	public ERegResponse register() {
		ERegResponse regResponse = ERegResponse.netError;
		if (!validateTransferPara(tParam)) {
			regResponse = ERegResponse.paramError;
			return regResponse;
		}
		String head = "reg";
		String registerInfo = tParam.ToJson();
	// wcfClient.setUrl("http://192.168.16.150:8086/PDATransferWS.svc");
		String result = wcfClient.register(registerInfo);
		if (!strUtil.isNullOrEmpty(result)) {
			if (result.startsWith(head)) {
				String[] regInfo = result.split(":");
				regResponse = ERegResponse.registered;
				if (regInfo.length > 1) {
					// ���浽�ļ�
					regResponse = ERegResponse.success;
					wirteRegisterInfo(regInfo[1], tParam);
				} else {
					if ("".equals(GlobalParas.getAppContext().getMD5())) {
						regResponse = ERegResponse.fail;
					} else {
						regResponse = ERegResponse.success;
					}
				}
			} else if (result.contains("�����쳣")) {
				regResponse = ERegResponse.netError;
			} else {
				regResponse = ERegResponse.other;
			}
		}
		return regResponse;
	}

	public LoginUser loginOnLine(String employeeNo, String pwd,
			StringBuilder msg) {
		LoginUser loginUser=null;
		if (!validateTransferPara(tParam)) {
			msg.append(EDownError.paramError.toString());
			return loginUser;
		}
		if (!isRegister(tParam)) {
			msg.append(EDownError.unRegisterError.toString());
			return loginUser;
		}
		String registerInfo = tParam.ToJson();
		String result = "";
		try {
			result = wcfClient.loginOnLine(registerInfo, employeeNo, pwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginUser;
	}

	/**
	 * дע����Ϣ
	 * 
	 * @param serverValidateInfo
	 * @param tParam2
	 */
	private void wirteRegisterInfo(String serverValidateInfo, TransferParam tp) {
		 
		GlobalParas.getAppContext().setMD5(serverValidateInfo);
		// GlobalParas.getAppContext().setCompanyMD5(
		// AESEncrypt.encrypt(tp.getEnterpriseID()));
		// GlobalParas.getAppContext().setPdaIdMD5(
		// AESEncrypt.encrypt(tp.getPdaId()));
	}

	/**
	 * ��֤��������
	 * 
	 * @param tp
	 * @return
	 */
	private boolean validateTransferPara(TransferParam tp) {
		if (tp == null) {
			return false;
		} else if (strUtil.isNullOrEmpty(tp.getEnterpriseID())) {
			return false;
		} else if (strUtil.isNullOrEmpty(tp.getPdaId())) {
			return false;
		} else if (strUtil.isNullOrEmpty(tp.getStationID())) {
			return false;
		}
		tp.setMd5("");
		return true;
	}

	/**
	 * ��֤�Ƿ�ע��
	 * 
	 * @param tp
	 * @return
	 */
	private boolean isRegister(TransferParam tp) {
		String md5 = GlobalParas.getAppContext().getMD5();
		if (strUtil.isNullOrEmpty(md5)) {
			return false;
		}
		tp.setMd5(md5);
		return true;
	}

	/**
	 * ����
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public String getDownData(ParamAnalyst pl) throws Exception {
		if (pl == null) {
			return EDownError.otherError.toString();
		}
		String param = pl.paramToString();
		String result = "";
		if (!validateTransferPara(tParam)) {
			return EDownError.paramError.toString();
		}
		if (!isRegister(tParam)) {
			return EDownError.unRegisterError.toString();
		}
		String transferPara = tParam.ToJson();
		result = wcfClient.downData(transferPara, param);
		if (!strUtil.isNullOrEmpty(result)) {
			if (tParam.isCompress()) {
				result = GZipUtil.gZipUnString(result);
			}
		}
		return result;
	}

	/***
	 * �ϴ�
	 * 
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public String uploadData(UpContentStructure contentStructure)
			throws Exception {
		if (contentStructure == null) {
			return EDownError.otherError.toString();
		}
		String content = contentStructure.getUploadStructure();
		String head = "anyType";
		String result = "";
		if (!validateTransferPara(tParam)) {
			return EDownError.paramError.toString();
		}
		if (!isRegister(tParam)) {
			return EDownError.unRegisterError.toString();
		}
		String transferPara = tParam.ToJson();
		if (tParam.isCompress()) {
			content = GZipUtil.gZipString(content);
		}
		result = wcfClient.uploadData(transferPara, content);
		if (result.startsWith(head)) {
			return EDownError.noError.toString();
		}
		return result;
	}

	/**
	 * ��չ����
	 * 
	 * @param op
	 * @param paramStr
	 * @return
	 * @throws Exception
	 */
	private String expandMethod(RequestOp op, String paramStr) throws Exception {
		String result = "";
		if (!validateTransferPara(tParam)) {
			return EDownError.paramError.toString();
		}
		if (!isRegister(tParam)) {
			return EDownError.unRegisterError.toString();
		}
		String transferPara = tParam.ToJson();
		StringBuilder paramSb = new StringBuilder();
		paramSb.append("<request><code>");
		paramSb.append(op.toString());
		paramSb.append("</code><param>");
		paramSb.append(paramStr);
		paramSb.append("</param></request>");
		result = wcfClient.expandMethod(transferPara, paramSb.toString());
		if (tParam.isCompress()) {
			result = GZipUtil.gZipUnString(result);
		}
		return result;
	}

	/**
	 * ���Ÿ���
	 * 
	 * @param barcode
	 * @return
	 * @throws Exception
	 */
	public String barcodeTrack(String barcode) throws Exception {
		return expandMethod(RequestOp.track, barcode);
	}

	/**
	 * ���ض���
	 * 
	 * @param stationName
	 * @param numId
	 * @param maxOrderTime
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	public String getOrder(String stationName, String numId,
			String maxOrderTime, String deviceId) throws Exception {
		String splitStr = ",";
		StringBuilder paramSb = new StringBuilder();
		paramSb.append(stationName).append(splitStr);
		paramSb.append(numId).append(splitStr);
		paramSb.append(maxOrderTime).append(splitStr);
		paramSb.append(deviceId);
		return expandMethod(RequestOp.getCallCenterOrderByID,
				paramSb.toString());
	}

	/**
	 * ��ȡ����״̬
	 * 
	 * @param deviceId
	 * @param maxOrderTime
	 * @param minOrderTime
	 * @return
	 * @throws Exception
	 */
	public String getOrderStatus(String deviceId, String maxOrderTime,
			String minOrderTime) throws Exception {
		String splitStr = ",";
		StringBuilder paramSb = new StringBuilder();
		paramSb.append(deviceId).append(splitStr);
		paramSb.append(minOrderTime).append(splitStr);
		paramSb.append(maxOrderTime);
		return expandMethod(RequestOp.getOrderStatus, paramSb.toString());
	}

	/**
	 * �����ϴ�
	 * 
	 * @return
	 * @throws Exception
	 */
	public String uploadOrder(UpOrderStructure orderStructure) throws Exception {
		if (orderStructure == null) {
			return EDownError.otherError.toString();
		}
		String param = orderStructure.toParamString();
		return expandMethod(orderStructure.getOp(), param);
	}
}
