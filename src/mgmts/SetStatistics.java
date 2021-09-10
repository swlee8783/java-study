/** public MgmtsStatisticsReqDto setStatistics(int type) {
        MgmtsStatisticsReqDto mgmtsStatisticsReqDto = new MgmtsStatisticsReqDto();
        List<MgmtsStatisticsReqDto.StatDateInfo> statDateList = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        mgmtsStatisticsReqDto.setType(type);
        mgmtsStatisticsReqDto.setClientId(clientId);
        mgmtsStatisticsReqDto.setInquiryDate(currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        mgmtsStatisticsReqDto.setStatDateCnt(statDateCnt);

        LocalDateTime aWeekAgo = currentDate.atStartOfDay().minusDays(7L);
        LocalDateTime yesterday = currentDate.atTime(LocalTime.MAX).minusDays(1L);

        // orgCode, apiCode가 Not Null이고 조회 일자 기준 전날부터 7일 동안의 apiTranHistory 내역 조회
        List<ApiTranHistEntity> apiTranHistEntityList = apiTranHistRepository
        .findAllByOrgCodeIsNotNullAndApiCodeIsNotNullAndInsertTimeBetween(type, aWeekAgo, yesterday);

        // 일자별(insertTime), orgCode 기준으로 groupby
        Map<LocalDate, Map<String, List<ApiTranHistEntity>>> apiTranHistoryPerDate = apiTranHistEntityList.stream()
        .collect(
        groupingBy(h -> h.getInsertTime().toLocalDate(),
        groupingBy(ApiTranHistEntity::getOrgCode)
        )
        );

        if (apiTranHistoryPerDate.isEmpty()) {
        List<MgmtsStatisticsReqDto.TmSlotInfo> tmSlotList = setTmSlotList();
        List<MgmtsStatisticsReqDto.ApiTypeInfo> apiTypeList = setApiTypeList(tmSlotList);
        List<MgmtsStatisticsReqDto.StatOrgInfo> orgList = setOrgList(apiTypeList);
        statDateList = setStatDateList(orgList, aWeekAgo.toLocalDate(), yesterday.toLocalDate());
        } else {
        for (LocalDate date = aWeekAgo.toLocalDate(); date.isBefore(currentDate); date = date.plusDays(1L)) {
        MgmtsStatisticsReqDto.StatDateInfo statDateInfo = new MgmtsStatisticsReqDto.StatDateInfo();

        Map<String, List<ApiTranHistEntity>> apiTranHistoryByOrgCode = apiTranHistoryPerDate.get(date);

        if (apiTranHistoryByOrgCode == null || apiTranHistoryByOrgCode.isEmpty()) {
        List<MgmtsStatisticsReqDto.TmSlotInfo> tmSlotList = setTmSlotList();
        List<MgmtsStatisticsReqDto.ApiTypeInfo> apiTypeList = setApiTypeList(tmSlotList);
        List<MgmtsStatisticsReqDto.StatOrgInfo> orgList = setOrgList(apiTypeList);
        statDateList.add(setStatDateList(orgList, date));
        } else {
        Set<String> orgCodeKeySet = apiTranHistoryByOrgCode.keySet();

        statDateInfo.setStatDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        statDateInfo.setOrgCnt(orgCodeKeySet.size());

        List<MgmtsStatisticsReqDto.StatOrgInfo> orgList = new ArrayList<>();

        for (String orgCode : orgCodeKeySet) {
        MgmtsStatisticsReqDto.StatOrgInfo statOrgInfo = new MgmtsStatisticsReqDto.StatOrgInfo();

        statOrgInfo.setOrgCode(orgCode);

        if (mgmtsStatisticsReqDto.getType() == IRREGULAR) {
        List<ApiTranHistEntity> apiTranHistList = apiTranHistoryByOrgCode.get(orgCode)
        .stream()
        .filter(h -> h.getApiDomainCode().getApiCode().equals("AU02")
        || h.getApiDomainCode().getApiCode().equals("AU03")
        || h.getApiDomainCode().getApiCode().equals("AU04")
        || h.getApiDomainCode().getApiCode().equals("AU11"))
        .sorted(Comparator.comparing(ApiTranHistEntity::getInsertTime).reversed())
        .collect(toList());

        // 신규 또는 변경 전송요구 수 count
        int consentNew = Math.toIntExact(apiTranHistList
        .stream()
        .filter(h -> h.getApiDomainCode().getApiCode().equals("AU02")
        || h.getApiDomainCode().getApiCode().equals("AU03")
        || h.getApiDomainCode().getApiCode().equals("AU11"))
        .count());

        // 철회된 전송요구 수 count
        int consentRevoke = Math.toIntExact(apiTranHistList
        .stream()
        .filter(h -> h.getApiDomainCode().getApiCode().equals("AU04"))
        .count());

        int consentOwn = 0;

        // 최종 유효한 전송요구 수 count, 철회 발생 시 기존 count 무효
        for (ApiTranHistEntity apiTranHistEntity : apiTranHistList) {
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

        // ApiCode 기준으로 groupby
        Map<String, List<ApiTranHistEntity>> apiTranHistoryByApiCode = apiTranHistoryByOrgCode
        .get(orgCode)
        .stream()
        .collect(groupingBy(h -> h.getApiDomainCode().getApiCode()));

        // ApiCode Key값 기준으로 ApiTypeCnt 구성
        Set<String> apiCodeKeySet = apiTranHistoryByApiCode.keySet();

        statOrgInfo.setApiTypeCnt(apiCodeKeySet.size());

        List<MgmtsStatisticsReqDto.ApiTypeInfo> apiTypeList = new ArrayList<>();

        for(String apiCode : apiCodeKeySet){
        MgmtsStatisticsReqDto.ApiTypeInfo apiTypeInfo = new MgmtsStatisticsReqDto.ApiTypeInfo();
        List<ApiTranHistEntity> apiTranHistEntitiesByApiCode = apiTranHistoryByApiCode.get(apiCode);

        apiTypeInfo.setApiType(apiCode);
        apiTypeInfo.setTmSlotCnt(tmSlotCnt);

        // tmSlot 기준으로 groupby
        Map<String, List<ApiTranHistEntity>> apiTranHistoryByTmSlot = apiTranHistEntitiesByApiCode
        .stream()
        .collect(groupingBy(ApiTranHistEntity::getTmSlot));

        List<MgmtsStatisticsReqDto.TmSlotInfo> tmSlotList = new ArrayList<>();

        // tmSlot별 조회 및 기입
        for (String tmSlot : tmSlots) {
        MgmtsStatisticsReqDto.TmSlotInfo tmSlotInfo = new MgmtsStatisticsReqDto.TmSlotInfo();
        if(apiTranHistoryByTmSlot.get(tmSlot)  == null) {
        tmSlotList.add(setTmSlotList(tmSlot));
        }
        else {
        // rspTime 계산을 위한 추출 및 List 구성
        List<Long> rspTimeList = apiTranHistoryByTmSlot
        .get(tmSlot)
        .stream()
        .map(ApiTranHistEntity::getResponseTime)
        .collect(toList());

        // statusCode 추출 및 List 구성
        List<Integer> statusCodeList = apiTranHistoryByTmSlot
        .get(tmSlot)
        .stream()
        .map(ApiTranHistEntity::getStatusCode)
        .collect(toList());

        double rspTotal = rspTimeList
        .stream()
        .mapToLong(j -> j).sum();
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
        }
        apiTypeInfo.setTmSlotList(tmSlotList);
        apiTypeList.add(apiTypeInfo);
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
        }

private MgmtsStatisticsReqDto.StatDateInfo setStatDateList(List<MgmtsStatisticsReqDto.StatOrgInfo> orgList, LocalDate date) {
        MgmtsStatisticsReqDto.StatDateInfo statDateInfo = new MgmtsStatisticsReqDto.StatDateInfo();

        statDateInfo.setStatDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        statDateInfo.setOrgCnt(DEFAULTINTVALUE);
        statDateInfo.setOrgList(orgList);

        return statDateInfo;
        }

private List<MgmtsStatisticsReqDto.StatDateInfo> setStatDateList(List<MgmtsStatisticsReqDto.StatOrgInfo> orgList, LocalDate startDate, LocalDate endDate) {
        List<MgmtsStatisticsReqDto.StatDateInfo> statDateInfoList = new ArrayList<>();

        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1L)); date = date.plusDays(1L)) {
        MgmtsStatisticsReqDto.StatDateInfo statDateInfo = new MgmtsStatisticsReqDto.StatDateInfo();

        statDateInfo.setStatDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        statDateInfo.setOrgCnt(DEFAULTINTVALUE);
        statDateInfo.setOrgList(orgList);
        statDateInfoList.add(statDateInfo);
        }

        return statDateInfoList;
        }

private List<MgmtsStatisticsReqDto.StatOrgInfo> setOrgList(List<MgmtsStatisticsReqDto.ApiTypeInfo> apiTypeList) {
        List<MgmtsStatisticsReqDto.StatOrgInfo> statOrgInfos = new ArrayList<>();
        MgmtsStatisticsReqDto.StatOrgInfo statOrgInfo = new MgmtsStatisticsReqDto.StatOrgInfo();

        statOrgInfo.setOrgCode(DEFAULTSTRVALUE);
        statOrgInfo.setConsentNew(DEFAULTINTVALUE);
        statOrgInfo.setConsentOwn(DEFAULTINTVALUE);
        statOrgInfo.setConsentRevoke(DEFAULTINTVALUE);
        statOrgInfo.setApiTypeCnt(DEFAULTINTVALUE);
        statOrgInfo.setApiTypeList(apiTypeList);
        statOrgInfos.add(statOrgInfo);

        return statOrgInfos;
        }

private List<MgmtsStatisticsReqDto.ApiTypeInfo> setApiTypeList(List<MgmtsStatisticsReqDto.TmSlotInfo> tmSlotList) {
        List<MgmtsStatisticsReqDto.ApiTypeInfo> apiTypeInfos = new ArrayList<>();
        MgmtsStatisticsReqDto.ApiTypeInfo apiTypeInfo = new MgmtsStatisticsReqDto.ApiTypeInfo();

        apiTypeInfo.setApiType(DEFAULTSTRVALUE);
        apiTypeInfo.setTmSlotCnt(tmSlotCnt);
        apiTypeInfo.setTmSlotList(tmSlotList);
        apiTypeInfos.add(apiTypeInfo);

        return apiTypeInfos;
        }

private List<MgmtsStatisticsReqDto.TmSlotInfo> setTmSlotList() {
        List<MgmtsStatisticsReqDto.TmSlotInfo> tmSlotInfos = new ArrayList<>();

        for (String tmSlot : tmSlots) {
        MgmtsStatisticsReqDto.TmSlotInfo tmSlotInfo = new MgmtsStatisticsReqDto.TmSlotInfo();

        tmSlotInfo.setTmSlot(tmSlot);
        tmSlotInfo.setRspTotal(DEFAULTSTRVALUE);
        tmSlotInfo.setRspAvg(DEFAULTSTRVALUE);
        tmSlotInfo.setRspStdev(DEFAULTSTRVALUE);
        tmSlotInfo.setSuccessApiCnt(DEFAULTINTVALUE);
        tmSlotInfo.setFailApiCnt(DEFAULTINTVALUE);
        tmSlotInfos.add(tmSlotInfo);
        }

        return tmSlotInfos;
        }

private MgmtsStatisticsReqDto.TmSlotInfo setTmSlotList(String tmSlot) {
        MgmtsStatisticsReqDto.TmSlotInfo tmSlotInfo = new MgmtsStatisticsReqDto.TmSlotInfo();

        tmSlotInfo.setTmSlot(tmSlot);
        tmSlotInfo.setRspTotal(DEFAULTSTRVALUE);
        tmSlotInfo.setRspAvg(DEFAULTSTRVALUE);
        tmSlotInfo.setRspStdev(DEFAULTSTRVALUE);
        tmSlotInfo.setSuccessApiCnt(DEFAULTINTVALUE);
        tmSlotInfo.setFailApiCnt(DEFAULTINTVALUE);

        return tmSlotInfo;
        }
*/