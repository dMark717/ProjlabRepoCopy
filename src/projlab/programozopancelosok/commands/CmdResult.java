package projlab.programozopancelosok.commands;

public enum CmdResult {
    SUCCESS("Line executed successfully.", true, true),
    COMMENT("Line was a comment.", true, true),
    ERR_BAD_CMD("Command not recognised.", false, false),
    ERR_ID_NOT_FOUND("ID was not found.", false, false),
    ERR_BAD_PARAM("The parameters are incorrect.", false, false),
    ERR_NOT_PLAYER("Invalid player name.", false, false),
    ERR_FATAL("Fatal error", false, false),
    ASSERT_SUCCESS("ASSERT SUCCESS", true, true),
    ASSERT_FAIL("ASSERTION FAILED", false, false);

    private String msg;
    private boolean isCmdSuccess;
    private boolean isRunSuccess;

    public boolean isCmdSuccess() {
        return isCmdSuccess;
    }

    public boolean isRunSuccess() {
        return isRunSuccess;
    }

    public String getMsg() {
        return msg;
    }

    CmdResult(String msg, boolean isCmdSuccess, boolean isRunSuccess) {
        this.msg = msg;
        this.isCmdSuccess = isCmdSuccess;
        this.isRunSuccess = isRunSuccess;
    }
}
