/**public MgmtsStatisticsReqDto setStatics(int type) {
 MgmtsStatisticsReqDto mgmtsStatisticsReqDto = new MgmtsStatisticsReqDto();
 mgmtsStatisticsReqDto.setClientId(clientId);

 LocalDate currentDate = LocalDate.now();

 mgmtsStatisticsReqDto.setInquiryDate(currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
 mgmtsStatisticsReqDto.setStatDateCnt(statDateCnt);
 mgmtsStatisticsReqDto.setType(type);

 LocalDateTime aWeekAgo = currentDate.atStartOfDay().minusDays(7L);
 LocalDateTime yesterday = currentDate.atTime(LocalTime.MAX).minusDays(1L);

 // orgCode, apiCode가 Not Null이고 조회 일자 기준 전날부터 7일 동안의 apiTranHistory 내역 조회
 List<ApiTranHistEntity> apiTranHistEntityList = apiTranHistRepository
 .findAllByOrgCodeIsNotNullAndApiCodeIsNotNullAndInsertTimeBetween(type, aWeekAgo, yesterday);

 // 일자별(insertTime), orgCode 기준으로 Group By
 Map<LocalDate, Map<String, List<ApiTranHistEntity>>> apiTranHistoryPerDate = apiTranHistEntityList.stream()
 .collect(
 groupingBy(h -> h.getInsertTime().toLocalDate(),
 groupingBy(ApiTranHistEntity::getOrgCode)
 )
 );

 List<MgmtsStatisticsReqDto.StatDateInfo> statDateList = new ArrayList<>();
 List<MgmtsStatisticsReqDto.StatOrgInfo> orgList = new ArrayList<>();
 List<MgmtsStatisticsReqDto.ApiTypeInfo> apiTypeList = new ArrayList<>();
 List<MgmtsStatisticsReqDto.TmSlotInfo> tmSlotList = new ArrayList<>();

 int consentNew;
 int consentRevoke;
 int consentOwn = 0;

 // 일자별(insertTime) 조회
 for (long i = 1; i < 8; i++) {
 LocalDate date = currentDate.minusDays(i);
 // 1차에서 filter 안됐을 경우 혹은 해당 날짜에 대한 history 정보가 없을 경우
 if (apiTranHistoryPerDate == null || apiTranHistoryPerDate.get(date) == null) {
 tmSlotList = setTmSlotList();

 apiTypeList = setApiTypeList(tmSlotList);

 orgList = setOrgList(apiTypeList);

 statDateList.add(setStatDateList(orgList, date));

 } else {
 MgmtsStatisticsReqDto.StatDateInfo statDateInfo = new MgmtsStatisticsReqDto.StatDateInfo();
 Map<String, List<ApiTranHistEntity>> apiTranHistoryByOrgCode = apiTranHistoryPerDate.get(date);


 Set<String> orgCodeKeySet = new HashSet<>();
 if (apiTranHistoryByOrgCode != null) {
 orgCodeKeySet = apiTranHistoryByOrgCode.keySet();
 }

 int orgCnt = orgCodeKeySet.size();

 statDateInfo.setStatDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
 statDateInfo.setOrgCnt(orgCnt);

 // orgCode별 조회
 if (orgCodeKeySet.isEmpty()) {
 tmSlotList = setTmSlotList();

 apiTypeList = setApiTypeList(tmSlotList);

 orgList = setOrgList(apiTypeList);
 } else {
 for (String orgCode : orgCodeKeySet) {
 MgmtsStatisticsReqDto.StatOrgInfo statOrgInfo = new MgmtsStatisticsReqDto.StatOrgInfo();

 statOrgInfo.setOrgCode(orgCode);

 // 비정기전송에 한해 consent_new, consent_revoke, consent_own 회신
 if (mgmtsStatisticsReqDto.getType() == IRREGULAR) {
 // apiCode 중 전송요구 관련 code filter를 통해 추출
 List<ApiTranHistEntity> apiTranHistoryList = apiTranHistoryByOrgCode.get(orgCode)
 .stream()
 .filter(h -> h.getApiDomainCode().getApiCode().equals("AU02")
 || h.getApiDomainCode().getApiCode().equals("AU03")
 || h.getApiDomainCode().getApiCode().equals("AU04")
 || h.getApiDomainCode().getApiCode().equals("AU11"))
 .sorted(Comparator.comparing(ApiTranHistEntity::getInsertTime).reversed())
 .collect(toList());

 // 신규 또는 변경 전송요구 수 count
 consentNew = (int) apiTranHistoryList.stream()
 .filter(h -> h.getApiDomainCode().getApiCode().equals("AU02")
 || h.getApiDomainCode().getApiCode().equals("AU03")
 || h.getApiDomainCode().getApiCode().equals("AU11")).count();

 // 철회된 전송요구 수 count
 consentRevoke = (int) apiTranHistoryList.stream()
 .filter(h -> h.getApiDomainCode().getApiCode().equals("AU04")).count();

 // 최종 유효한 전송요구 수 count, 철회 발생 시 기존 count 무효
 for (ApiTranHistEntity apiTranHistEntity : apiTranHistoryList) {
 if (apiTranHistEntity.getApiDomainCode().getApiCode().equals("AU02")
 || apiTranHistEntity.getApiDomainCode().getApiCode().equals("AU03")
 || apiTranHistEntity.getApiDomainCode().getApiCode().equals("AU11")) {
 consentOwn++;
 } else {
 consentOwn = 0;
 }
 }

 statOrgInfo.setConsentNew(consentNew);
 statOrgInfo.setConsentRevoke(consentRevoke);
 statOrgInfo.setConsentOwn(consentOwn);
 } // 정기전송에서 미회신 처리 부분
 else {
 statOrgInfo.setConsentNew(null);
 statOrgInfo.setConsentRevoke(null);
 statOrgInfo.setConsentOwn(null);
 }

 // ApiCode 기준으로 Group By
 Map<String, List<ApiTranHistEntity>> apiTranHistoryByApiCode = apiTranHistoryByOrgCode
 .get(orgCode)
 .stream()
 .collect(groupingBy(h -> h.getApiDomainCode().getApiCode()));

 // ApiCode Key 값 기준으로 ApiTypeCnt 구성
 Set<String> apiCodeKeySet = new HashSet<>();
 if (apiTranHistoryByApiCode != null) {
 apiCodeKeySet = apiTranHistoryByApiCode.keySet();
 }

 int apiCodeKeySetSize = apiCodeKeySet.size();
 statOrgInfo.setApiTypeCnt(apiCodeKeySetSize);

 if (apiCodeKeySet.isEmpty()) {
 tmSlotList = setTmSlotList();

 apiTypeList = setApiTypeList(tmSlotList);
 } else {
 // apiCode별 조회
 for (String apiCode : apiCodeKeySet) {
 MgmtsStatisticsReqDto.ApiTypeInfo apiTypeInfo = new MgmtsStatisticsReqDto.ApiTypeInfo();
 List<ApiTranHistEntity> apiTranHistEntitiesByApiCode = apiTranHistoryByApiCode.get(apiCode);

 apiTypeInfo.setApiType(apiCode);
 apiTypeInfo.setTmSlotCnt(tmSlotCnt);

 // tmSlot 기준으로 Group By
 Map<String, List<ApiTranHistEntity>> apiTranHistoryByTmSlot = apiTranHistEntitiesByApiCode.stream().collect(
 groupingBy(ApiTranHistEntity::getTmSlot)
 );

 // tmSlot별 조회 및 기입
 for (String tmSlot : tmSlots) {
 // tmSlot 정보가 없거나 null일 경우 처리
 if (apiTranHistoryByTmSlot == null || apiTranHistoryByTmSlot.get(tmSlot) == null) {
 tmSlotList.add(setTmSlotList(tmSlot));
 } else {
 MgmtsStatisticsReqDto.TmSlotInfo tmSlotInfo = new MgmtsStatisticsReqDto.TmSlotInfo();
 List<ApiTranHistEntity> apiTranHistoryForTmSlotList = apiTranHistoryByTmSlot.get(tmSlot);

 // rspTime 계산을 위한 추출 및 List 구성
 List<Long> rspTimeList = apiTranHistoryForTmSlotList.stream()
 .map(ApiTranHistEntity::getResponseTime)
 .collect(toList());

 // statusCode 추출 및 List 구성
 List<Integer> statusCodeList = apiTranHistoryForTmSlotList.stream()
 .map(ApiTranHistEntity::getStatusCode)
 .collect(toList());

 double rspTotal = rspTimeList.stream().mapToLong(j -> j).sum();
 double rspAvg = 0;
 double rspStdev = 0;

 if (!rspTimeList.isEmpty()) {

 // rspTime 평균값 계산
 rspAvg = Math.round((rspTotal / rspTimeList.size()) * 1000) / 1000.0;
 double sum = 0;

 for (Long rspTime : rspTimeList) {
 sum += Math.pow(rspTime - rspAvg, 2);
 }

 // rspTime 표준편차 계산
 if (rspAvg != 0) {
 rspStdev = Math.round(Math.sqrt(sum / rspAvg) * 1000) / 1000.0;
 }
 }

 int successCnt = 0;
 int failCnt = 0;

 // statusCode 성공, 실패 여부 구분 및 count
 for (Integer statusCode : statusCodeList) {
 if (statusCode.equals(successCode)) {
 successCnt++;
 } else {
 failCnt++;
 }
 }
 tmSlotInfo.setTmSlot(tmSlot);
 tmSlotInfo.setRspAvg(String.valueOf(rspAvg));
 tmSlotInfo.setRspTotal(String.valueOf(rspTotal));
 tmSlotInfo.setRspStdev(String.valueOf(rspStdev));
 tmSlotInfo.setSuccessApiCnt(successCnt);
 tmSlotInfo.setFailApiCnt(failCnt);

 tmSlotList.add(tmSlotInfo);
 }
 apiTypeInfo.setTmSlotList(tmSlotList);

 }
 tmSlotList.clear();
 apiTypeList.add(apiTypeInfo);
 }
 }
 statOrgInfo.setApiTypeList(apiTypeList);
 orgList.add(statOrgInfo);
 }
 statDateInfo.setOrgList(orgList);
 statDateList.add(statDateInfo);
 }

 }

 }
 mgmtsStatisticsReqDto.setStatDateList(statDateList);
 logger.info("mgmtsStatisticsReqDto: {}", mgmtsStatisticsReqDto);
 return mgmtsStatisticsReqDto;
 }*/
