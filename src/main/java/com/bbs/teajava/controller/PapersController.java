package com.bbs.teajava.controller;


import com.bbs.teajava.annotation.Authentication;
import com.bbs.teajava.service.IPapersService;
import com.bbs.teajava.utils.ApiResultUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 论文接口
 * </p>
 *
 * @author hk
 * @since 2024-11-25
 */
@RestController
@RequestMapping("/Papers")
@RequiredArgsConstructor
public class PapersController {

    private final IPapersService papersService;

    @RequestMapping(value = "/GetAllPapers", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("获取所有论文")
    public ApiResultUtils getAllPapers() {
        return ApiResultUtils.success(papersService.getAllPapers());
    }

    @RequestMapping(value = "GetPaperListByPage", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("分页获取论文列表")
    public ApiResultUtils getPaperListByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return ApiResultUtils.success(papersService.getPaperListByPage(page, pageSize));
    }

    @RequestMapping(value = "GetUserPaperListByPage", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("分页获取用户论文列表")
    public ApiResultUtils getUserPaperListByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                 @RequestParam(value = "userId") Integer userId) {
        return ApiResultUtils.success(papersService.getUserPaperListByPage(page, pageSize, userId));
    }

    @RequestMapping(value = "SavePapers", method = {RequestMethod.POST})
    @ApiOperation("新增/修改论文, 修改论文需要传入论文id，是否存在附件: 0: 不存在, 1: 存在")
    @Authentication(requireReporter = true)
    public ApiResultUtils savePapers(@RequestParam(value = "paperId", required = false) Integer paperId,
                                     @RequestParam(value = "title") String title,
                                     @RequestParam(value = "conference") String conference,
                                     @RequestParam(value = "paperFile") MultipartFile paperFile,
                                     @RequestParam(value = "attachmentTag") Integer attachmentTag) {
        return papersService.savePapers(paperId, title, conference, paperFile, attachmentTag);
    }

    @RequestMapping(value = "GetPaperById", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("根据论文id获取论文详情")
    public ApiResultUtils getPaperById(@RequestParam(value = "paperId") Integer paperId) {
        return ApiResultUtils.success(papersService.getPaperById(paperId));
    }


    @RequestMapping(value = "UploadTempFile", method = {RequestMethod.POST})
    @ApiOperation("上传论文附件临时文件")
    @Authentication(requireReporter = true)
    public ApiResultUtils uploadTempFile(@RequestParam(value = "attachment") MultipartFile attachment) {
        return papersService.uploadTempFile(attachment);
    }

    @RequestMapping(value = "DeletePaper", method = {RequestMethod.POST})
    @ApiOperation("删除论文")
    @Authentication
    public ApiResultUtils deletePaper(@RequestParam(value = "paperId") Integer paperId) {
        return papersService.deletePaper(paperId);
    }

    @RequestMapping(value = "Download", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiResponse(description = "下载论文/论文附件 type: 0:论文, 1:论文附件")
    public void download(@RequestParam(value = "paperId") Integer paperId,
                         @RequestParam(value = "type") Integer type) throws Exception {
        papersService.download(paperId, type);
    }

}
