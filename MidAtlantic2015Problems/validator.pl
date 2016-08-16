#!/usr/bin/perl
#
#  A generic validator used to run large input sets
#
#   Usage: validator.pl inputDataFile outputFile answerFile resultFile
#

($inputFile, $outputFile, $expectedFile, $resultFile) = @ARGV;

$trueInputFile="/home/zeil/usr/courses/acm/contest12/ProblemSet/DuelingPhilosophers/duelingphilosophers.judge";
$trueExpectedFile="/home/zeil/usr/courses/acm/contest12/ProblemSet/DuelingPhilosophers/duelingphilosophers.out";

$javaCommand = "java -Xmx512M";

$goodOutcome="accepted";
$badOutcome="No - Wrong Answer";
$crashOutcome="No - Runtime error";

open (LOG, ">log.txt");
print LOG "$inputFile, $outputFile, $expectedFile, $resultFile\n";

$sourceFile = "";
opendir (DIR, ".") or die $!;
while (my $file = readdir(DIR)) {
  if ($file =~ /\.java$/) {
    $sourceFile = $file;
  } elsif ($file =~ /\.cpp$/) {
    $sourceFile = $file;
  }
}
closedir(DIR);

print LOG "$sourceFile\n";

$status = 0;
if ($sourceFile =~ /\.java$/) {
  $className = $sourceFile;
  $className =~ s/\.java$//;
  system ("$javaCommand $className < $trueInputFile > $outputFile");
} else {
  $progName = $sourceFile;
  $progName =~ s/\.cpp$/.exe/;
  print LOG "progName = $progName\n";
  system ("./$progName < $trueInputFile > $outputFile");
}
$status = $?;
print LOG "status = $status\n";

$outcome = "";
if ($status == 0) {
  system ("diff $outputFile $trueExpectedFile > diff.txt");
  $status = $?;
  print LOG "diffstatus = $status\n";
  if ($status == 0) {
    $outcome = $goodOutcome;
  } else {
    $outcome = $badOutcome;
  }
} else {
  $outcome = $crashOutcome;
}

close LOG;

open RESULT, ">$resultFile";
print RESULT "<?xml version=\"1.0\"?>\n";
print RESULT "<result outcome=\"$outcome\" security=\"$resultFile\"> </result>\n";
close RESULT;
